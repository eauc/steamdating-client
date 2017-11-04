require "json"
require_relative "../pages/rounds_next"
require_relative "../pages/rounds_nth"
require_relative "../pages/rounds_summary"

Given("I open Rounds/Summary page") do
  @page = Pages::RoundsSummary.new
            .load
            .filter(" ")
  @sort_by = "Player"
end

Given("I open Rounds/Next page") do
  @page = Pages::RoundsNext.new
            .load
end

Given(/^I open Rounds\/(\d+) page$/) do |nth|
  @page = Pages::RoundsNth.new(nth)
            .load
            .filter_with(" ")
end

When("I enter games:") do |table|
  @page.enter_games(table.symbolic_hashes)
end

When("I select \"$player\" name \"$name\" for game \#$game") do |player, name, game|
  @page.set_player_name(game.to_i - 1, player.to_sym, name)
end

When("I ask for a SR pairing suggestion") do
  @page.ask_sr_suggestion
end

When("I create the Next Round") do
  @new_round_index = @tournament["rounds"].length
  sleep 0.5
  @page.create_round
end

# When("I go from Summary to Round \#$n") do |n|
#   @page.go_to_round(n)
# end

When("I filter the Summary with \"$filter\"") do |filter_value|
  @page.filter(filter_value)
end

When("I filter the Round with \"$filter\"") do |filter_value|
  @page.filter_with(filter_value)
end

When("I sort the Round by \"$by\"") do |by|
  @page.sort_by(by)
  @sort_by = by
end

When("I invert the Round sort order") do
  @page.invert_sort_by(@sort_by)
end

When("I sort the Summary by \"$sort\"") do |sort|
  @page.invert_sort_by(sort)
  @sort_by = sort
end

When("I invert the Summary sort order") do
  @page.invert_sort_by(@sort_by)
end

When("I delete current Round") do
  @page.delete_round
end

Then("I can edit the Next Round information") do
  @page
    .expect_games_forms_for_players(@tournament["players"])
    .expect_games_selects_for_players(@tournament["players"])
end

Then("I can edit the Next Round information for players:") do |table|
  @active_players = table.raw[0].map {|n| {"name" => n}}
  @page
    .expect_games_forms_for_players(@active_players)
    .expect_games_selects_for_players(@active_players)
end

Then("\"$player\" for game \#$game is empty") do |player, game|
  sleep 0.5
  @page.expect_empty_pairing(game.to_i - 1, player.to_sym)
end

Then("I can create next Round") do
  @page.expect_start_enabled
end

Then("I cannot create the Next Round") do
  @page.expect_start_disabled
end

Then("I see an error with the unpaired players:") do |table|
  @page.expect_unpaired_players_error(table.raw);
end

Then("I see an error with the already played pairings:") do |table|
  @page.expect_already_played(table.raw)
end

Then("next Round has no error or warning") do
  @page.expect_no_error_warning
end

matching_games = {
  "to" => [
    { p1ap: 32, p1cp: 3, p1list: "Amon1", player1: 'titi', table: 2, player2: 'toto', p2list: "Bethayne1", p2cp: 5, p2ap: 75 },
    { p1ap: 46, p1cp: 0, p1list: "Lylyth2", player1: 'toutou', table: 3, player2: 'tutu', p2list: "Bartolo1", p2cp: 4, p2ap: 30 },
  ],
  "te" => [
    { p1ap: 52, p1cp: 5, p1list: "Butcher2", player1: 'tete', table: 1, player2: 'teuteu', p2list: "Vyros1", p2cp: 3, p2ap: 21 },
  ],
};

Then("I see the Round filtered with \"$filter\"") do |filter|
  @page.expect_games(matching_games[filter])
end

sorted_games = {
  "Player2" => [
    { p1ap: 0, p1cp: 0, player1: 'tyty', table: 4, player2: 'Phantom', p2cp: 0, p2ap: 0 },
    { p1ap: 52, p1cp: 5, p1list: "Butcher2", player1: 'tete', table: 1, player2: 'teuteu', p2list: "Vyros1", p2cp: 3, p2ap: 21 },
    { p1ap: 32, p1cp: 3, p1list: "Amon1", player1: 'titi', table: 2, player2: 'toto', p2list: "Bethayne1", p2cp: 5, p2ap: 75 },
    { p1ap: 46, p1cp: 0, p1list: "Lylyth2", player1: 'toutou', table: 3, player2: 'tutu', p2list: "Bartolo1", p2cp: 4, p2ap: 30 },
  ],
  "Table" => [
    { p1ap: 52, p1cp: 5, p1list: "Butcher2", player1: 'tete', table: 1, player2: 'teuteu', p2list: "Vyros1", p2cp: 3, p2ap: 21 },
    { p1ap: 32, p1cp: 3, p1list: "Amon1", player1: 'titi', table: 2, player2: 'toto', p2list: "Bethayne1", p2cp: 5, p2ap: 75 },
    { p1ap: 46, p1cp: 0, p1list: "Lylyth2", player1: 'toutou', table: 3, player2: 'tutu', p2list: "Bartolo1", p2cp: 4, p2ap: 30 },
    { p1ap: 0, p1cp: 0, player1: 'tyty', table: 4, player2: 'Phantom', p2cp: 0, p2ap: 0 },
  ],
};

Then("I see the Round sorted by \"$by\"") do |by|
  @page.expect_games(sorted_games[by])
end

Then("I see the Round sorted by \"$by\" in reverse order") do |by|
  @page.expect_games(sorted_games[by].reverse)
end

Then("I see the Rounds/Summary page with rounds:") do |table|
  @round_summary = table.raw
  @page = Pages::RoundsSummary.new
            .expect_rounds_summary(@round_summary)
end

Then("I see the Rounds/Summary page with the same rounds in reverse order") do
  @page.expect_rounds_summary(@round_summary.reverse)
end

Then("I see Rounds/$nth page with games:") do |nth, table|
  within(Pages::PAGE_CONTENT) do
    expect(page).to have_content("Round ##{nth}")
  end
  Pages::RoundsNth.new(nth)
    .expect_games(table.symbolic_hashes)
end

# Then("I see the Round \#$n games") do |n|
#   Pages::RoundsNth.new(n)
#     .expect_games(games[n])
# end

Then("player \"$name\" is in Next Round pairings") do |name|
  @page.expect_player_paired(name)
end

Then("player \"$name\" is not in Next Round pairings") do |name|
  @page.expect_player_not_paired(name)
end

Then("I can edit $n pairings for Next Round") do |n|
  @page.expect_n_games_forms(n.to_i)
end

# Then("I see Round \#$nth games:") do |nth, table|
#   @page.expect_round(nth, table.symbolic_hashes)
# end
