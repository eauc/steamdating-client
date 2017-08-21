Given(/^I open Rounds\/Next page$/) do
  @page = Pages::Rounds.new
            .load
            .start_edit_next
end

Then(/^I can edit the Next Round information$/) do
  @page
    .expect_games_forms_for_players(@tournament["players"])
    .expect_games_selects_for_players(@tournament["players"])
end
