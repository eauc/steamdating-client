Given(/^I open Rounds\/Next page$/) do
  @page = Pages::Rounds.new
            .load
            .start_edit_next
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

Then(/^I see the New Round's page$/) do
  within(".sd-PageContent") do
    expect(page).to have_content("Round ##{@new_round_index+1}")
  end
  @page.expect_games(@new_round_games)
end
