Given(/^I open Rounds\/Next page$/) do
  @page = Pages::Rounds.new
            .load
            .start_edit_next
end

Given(/^some players are paired$/) do
  step("I pair some players")
end

When(/^I pair all available players$/) do
  indexed_players_names = @tournament["players"]
                            .each_with_index
                            .map { |p,i| [i, p["name"]] }
  @page
    .set_players_names(indexed_players_names)
    .set_tables((indexed_players_names.length / 2).floor + 1)
  @new_round_games = @tournament["players"]
                       .map { |p| p["name"] }
                       .each_slice(2)
                       .each_with_index
                       .map do |pair, index|
    {
      p1: pair[0],
      table: index+1,
      p2: pair[1] || "Phantom",
    }
  end
end

When(/^I pair some players$/) do
  players_names = @tournament["players"].map {|p| p["name"]}
  paired_indices = [0, 1, 3, 5]
  @unpaired_indices = (0...players_names.length).to_a - paired_indices
  @paired_players_names = paired_indices.map {|i| players_names[i]}
  @unpaired_players_names = @unpaired_indices.map {|i| players_names[i]}
  @page.set_players_names(paired_indices.map {|i| [i, players_names[i]]})
end

When(/^I select a player who is already paired$/) do
  @changed_pairing_index = 1
  @changed_pairing_name = @paired_players_names[@changed_pairing_index]
  @page.set_player_name(@unpaired_indices[0], @changed_pairing_name)
  sleep 0.25
end

When(/^I create the Next Round$/) do
  @new_round_index = @tournament["rounds"].length
  sleep 0.5
  @page.create_round
end

Then(/^I can edit the Next Round information$/) do
  @page
    .expect_games_forms_for_players(@tournament["players"])
    .expect_games_selects_for_players(@tournament["players"])
end

Then(/^the player name is removed from its previous pairing$/) do
  @page.expect_empty_pairing(@changed_pairing_index)
end

Then(/^I cannot create the Next Round$/) do
  within(".sd-PageContent") do
    expect_submit_disabled
  end
end

Then(/^I see the New Round's page$/) do
  within(".sd-PageContent") do
    expect(page).to have_content("Round ##{@new_round_index+1}")
  end
  @page.expect_games(@new_round_games)
end

Then(/^I see an error with the unpaired players names$/) do
  @page.expect_unpaired_players_error(@unpaired_players_names);
end
