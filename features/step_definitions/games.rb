require_relative "../pages/games_edit"

When("I start to edit Game with player \"$name\"") do |name|
  @page.start_edit_game_with_player(name)
  @page = Pages::GamesEdit.new
end

When("I enter Game results:") do |table|
  @page.fill_game_form(table.symbolic_hashes[0])
end

When("I save the Game") do
  @page.save_game
end

Then("I can edit the Game information:") do |table|
  @page.expect_game_form(table.symbolic_hashes[0])
end
