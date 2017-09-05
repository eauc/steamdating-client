require_relative "../pages/games_edit"

When("I enter Game results:") do |table|
  @page.fill_game_form(table.symbolic_hashes[0])
end

When("I save the Game") do
  @page.save_game
end

Then("I can edit the Game information:") do |table|
  @page.expect_game_form(table.symbolic_hashes[0])
end
