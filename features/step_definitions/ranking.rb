require_relative "../pages/ranking"

When("I open Ranking page") do
  @page = Pages::Ranking.new
            .load
            .filter_with(" ")
end

When("I filter the Ranking with \"$filter\"") do |filter|
  @page.filter_with(filter)
  @filter_value = filter
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

Then("I see the Ranking:") do |table|
  @page.expect_ranking_list(table.raw)
end

Then("I see the Bests in faction:") do |table|
  @page.expect_bests_in_faction(table.raw)
end

Then("I see the Bests scores:") do |table|
  @page.expect_bests_scores(table.raw)
end

matching_ranking = {
  "te" => [
    [3, "tete", "Khador", 2, 7, 38, 616, 1, "Drop now"],
    [4, "teuteu", "Retribution", 2, 6, 25, 793, 1, "Drop now"],
  ],
  "to" => [
    [2, "toto", "Legion", 2, 7, 41, 872, 2, "Drop now"],
    [7, "toutou", "Legion", 0, 4, 19, 639, 3, "Drop now"],
  ],
}

Then("I see the matching players in Ranking") do
  @page.expect_ranking_list(matching_ranking[@filter_value])
end

sorted_ranking = {
  "Player" => [
    [ 3,  "tete",    "Khador",         2,    7,  38,  616,   1, "Drop now"],
    [ 4,  "teuteu",  "Retribution",    2,    6,  25,  793,   1, "Drop now"],
    [ 6,  "titi",    "Protectorate",   1,    6,  12,  332,   1, "Drop now"],
    [ 2,  "toto",    "Legion",         2,    7,  41,  872,   2, "Drop now"],
    [ 7,  "toutou",  "Legion",         0,    4,  19,  639,   3, "Drop now"],
    [ 5,  "tutu",    "Mercenaries",    1,    6,  26,  699,   2, "Drop now"],
    [ 1,  "tyty",    "Protectorate",   4,    5,  37,  250,   2, "Drop now"],
  ],
  "SOS" => [
    [ 7,  "toutou",  "Legion",         0,    4,  19,  639,   3, "Drop now"],
    [ 1,  "tyty",    "Protectorate",   4,    5,  37,  250,   2, "Drop now"],
    [ 4,  "teuteu",  "Retribution",    2,    6,  25,  793,   1, "Drop now"],
    [ 5,  "tutu",    "Mercenaries",    1,    6,  26,  699,   2, "Drop now"],
    [ 6,  "titi",    "Protectorate",   1,    6,  12,  332,   1, "Drop now"],
    [ 2,  "toto",    "Legion",         2,    7,  41,  872,   2, "Drop now"],
    [ 3,  "tete",    "Khador",         2,    7,  38,  616,   1, "Drop now"],
  ],
}

Then("I see the Ranking sorted by \"$sort\"") do |sort|
  @page.expect_ranking_list(sorted_ranking[sort])
end

Then("I see the Ranking sorted by \"$sort\" in reverse order") do |sort|
  @page.expect_ranking_list(sorted_ranking[sort].reverse)
end

Then("I see the player \"$name\" has droped after round \#$nth") do |name, nth|
  @page.expect_player_droped_after(name, nth)
end

Then("I see the player \"$name\" has not droped") do |name|
  @page.expect_player_not_droped(name)
end
