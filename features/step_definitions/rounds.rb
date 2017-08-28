Given(/^some Rounds have been defined$/) do
  @tournament = JSON.parse(File.read("features/data/someRounds.json"))
  Pages::File.new
    .load
    .open("someRounds.json")
  expect_toaster("File loaded")
  validate_prompt
end

Given(/^I open Rounds\/Next page$/) do
  @page = Pages::Rounds.new
            .load
            .start_edit_next
end

Given(/^I open Rounds\/(\d+) page$/) do |n|
  @page = Pages::Rounds.new
            .load
            .start_nth(n)
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

When(/^I filter the Round with "([^"]*)"$/) do |filter_value|
  @page.filter(filter_value)
  @filter_value = filter_value
end

When(/^I sort the Round by "([^"]*)"$/) do |by|
  @page.sort_by(by)
  @sort_by = by
end

When(/^I invert the Round sort order$/) do
  @page.invert_sort_by(@sort_by)
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

matching_games = {
  "to" => [
    { p1ap: 32, p1cp: 3, p1: 'titi', table: 2, p2: 'toto', p2cp: 5, p2ap: 75 },
    { p1ap: 46, p1cp: 0, p1: 'toutou', table: 3, p2: 'tutu', p2cp: 4, p2ap: 30 },
  ],
  "te" => [
    { p1ap: 52, p1cp: 5, p1: 'tete', table: 1, p2: 'teuteu', p2cp: 3, p2ap: 21 },
  ],
};

Then(/^I see the matching Games$/) do
  @page.expect_games(matching_games[@filter_value])
end

sorted_games = {
  "Player2" => [
    { p1ap: 0, p1cp: 0, p1: 'tyty', table: 4, p2: 'Phantom', p2cp: 0, p2ap: 0 },
    { p1ap: 52, p1cp: 5, p1: 'tete', table: 1, p2: 'teuteu', p2cp: 3, p2ap: 21 },
    { p1ap: 32, p1cp: 3, p1: 'titi', table: 2, p2: 'toto', p2cp: 5, p2ap: 75 },
    { p1ap: 46, p1cp: 0, p1: 'toutou', table: 3, p2: 'tutu', p2cp: 4, p2ap: 30 },
  ],
  "Table" => [
    { p1ap: 52, p1cp: 5, p1: 'tete', table: 1, p2: 'teuteu', p2cp: 3, p2ap: 21 },
    { p1ap: 32, p1cp: 3, p1: 'titi', table: 2, p2: 'toto', p2cp: 5, p2ap: 75 },
    { p1ap: 46, p1cp: 0, p1: 'toutou', table: 3, p2: 'tutu', p2cp: 4, p2ap: 30 },
    { p1ap: 0, p1cp: 0, p1: 'tyty', table: 4, p2: 'Phantom', p2cp: 0, p2ap: 0 },
  ],
};

Then(/^I see the Round sorted by "([^"]*)"$/) do |by|
  @page.expect_games(sorted_games[by])
end

Then(/^I see the Round sorted by "([^"]*)" in reverse order$/) do |by|
  @page.expect_games(sorted_games[by].reverse)
end
