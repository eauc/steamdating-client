require_relative "../pages/ranking"

When("I open Ranking page") do
  @page = Pages::Ranking.new
            .load
            .filter_with(" ")
end

When("I filter the Ranking with \"$filter\"") do |filter|
  @page.filter_with(filter)
end

When("I sort the Ranking by \"$sort\"") do |sort|
  @page.sort_by(sort)
  @sort_by = sort
end

When("I invert the Ranking sort order") do
  @page.sort_by(@sort_by)
end

When("I drop player \"$name\"") do |name|
  @page.drop_player(name)
end

When("I un\-drop player \"$name\"") do |name|
  @page.undrop_player(name)
end

Then("I see the Rankings page:") do |table|
  @page.expect_ranking_list(table.raw)
end

Then("I see the Bests in faction:") do |table|
  @page.expect_bests_in_faction(table.raw)
end

Then("I see the Bests scores:") do |table|
  @page.expect_bests_scores(table.raw)
end

Then("I see the player \"$name\" has droped after round \#$nth") do |name, nth|
  @page.expect_player_droped_after(name, nth)
end

Then("I see the player \"$name\" has not droped") do |name|
  @page.expect_player_not_droped(name)
end
