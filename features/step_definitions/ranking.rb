require_relative "../pages/ranking"

When("I open Ranking page") do
  @page = Pages::Ranking.new
            .load
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
    [3, "tete", "Khador", 2, 7, 38, 616, 1],
    [4, "teuteu", "Retribution", 2, 6, 25, 793, 1],
  ],
  "to" => [
    [2, "toto", "Legion", 2, 7, 41, 872, 2,],
    [7, "toutou", "Legion", 0, 4, 19, 639, 3,],
  ],
}

Then("I see the matching players in Ranking") do
  @page.expect_ranking_list(matching_ranking[@filter_value])
end

sorted_ranking = {
  "Player" => [
    [ 3,  "tete",    "Khador",         2,    7,  38,  616,   1],
    [ 4,  "teuteu",  "Retribution",    2,    6,  25,  793,   1],
    [ 6,  "titi",    "Protectorate",   1,    6,  12,  332,   1],
    [ 2,  "toto",    "Legion",         2,    7,  41,  872,   2],
    [ 7,  "toutou",  "Legion",         0,    4,  19,  639,   3],
    [ 5,  "tutu",    "Mercenaries",    1,    6,  26,  699,   2],
    [ 1,  "tyty",    "Protectorate",   4,    5,  37,  250,   2],
  ],
  "SOS" => [
    [ 7,  "toutou",  "Legion",         0,    4,  19,  639,   3],
    [ 1,  "tyty",    "Protectorate",   4,    5,  37,  250,   2],
    [ 4,  "teuteu",  "Retribution",    2,    6,  25,  793,   1],
    [ 5,  "tutu",    "Mercenaries",    1,    6,  26,  699,   2],
    [ 6,  "titi",    "Protectorate",   1,    6,  12,  332,   1],
    [ 2,  "toto",    "Legion",         2,    7,  41,  872,   2],
    [ 3,  "tete",    "Khador",         2,    7,  38,  616,   1],
  ],
}

Then("I see the Ranking sorted by \"$sort\"") do |sort|
  @page.expect_ranking_list(sorted_ranking[sort])
end

Then("I see the Ranking sorted by \"$sort\" in reverse order") do |sort|
  @page.expect_ranking_list(sorted_ranking[sort].reverse)
end
