require "json"
require_relative "../pages/file"
require_relative "../pages/players_create"
require_relative "../pages/players_edit"
require_relative "../pages/players_list"

Given("I open Players/List page") do
  @page = Pages::PlayersList.new
            .load
end

Given("I open Players/Create page") do
  @page = Pages::PlayersCreate.new
            .load
end

Given("some Players have been defined") do
  @tournament = JSON.parse(File.read("features/data/somePlayers.json"))
  Pages::File.new
    .load
    .open("somePlayers.json")
  expect_toaster("File loaded")
  validate_prompt
end

Given("more Players have been defined") do
  @tournament = JSON.parse(File.read("features/data/morePlayers.json"))
  Pages::File.new
    .load
    .open("morePlayers.json")
  expect_toaster("File loaded")
  validate_prompt
end

When("I start to create player") do
  @page.start_create_player
  @page = Pages::PlayersCreate.new
end

When("I start to edit player \"$name\"") do |name|
  @page.start_edit_player(name)
  @page = Pages::PlayersEdit.new
end

When("I filter the Players list with \"$filter\"") do |filter|
  @filter_value = filter
  @page.filter_with(filter)
end

When("I sort the Players list by \"$by\"") do |sort|
  @sort_value = sort
  @page.sort_by(sort)
end

When("I invert the sort order") do
  @page.invert_sort_by(@sort_value)
end

When("I enter player information:") do |json_info|
  @page.fill_player_form(JSON.parse(json_info))
end

When("I create the player") do
  @page.submit
end

When("I save the player") do
  @page.submit
end

When("I delete player \"$name\"") do |name|
  @page = Pages::PlayersEdit.new
            .load(name)
            .delete_player
  validate_prompt
end

Then("I can edit the player information") do
  within(Pages::PAGE_CONTENT) do
    expect(page).to have_field("Name")
    expect(page).to have_field("Origin")
    expect(page).to have_field("Faction")
    expect(page).to have_field("Lists")
    expect(page).to have_field("Notes")
  end
end

Then("I can edit the player information with:") do |json_info|
  info = JSON.parse(json_info)
  within(Pages::PAGE_CONTENT) do
    expect(page).to have_field("Name", with: info.fetch("name", ""))
    expect(page).to have_field("Origin", with: info.fetch("origin", ""))
    expect(page).to have_field("Faction", with: info.fetch("faction", ""))
    expect(page).to have_field("Lists")
    expect(page.find_field("Lists").value).to eq(info.fetch("lists", []))
    expect(page).to have_field("Notes", with: info.fetch("notes", ""))
  end
end

Then("I see Players/List page with player:") do |table|
  expect(Pages::Players.new).to be_loaded
  Pages::PlayersList.new
    .expect_player_in_list(table.raw[0])
end

Then("I see Players/List page without player \"$name\"") do |name|
  expect(Pages::Players.new).to be_loaded
  within(Pages::PAGE_CONTENT) do
    within("tbody") do
      expect(page).to have_no_content(name)
    end
  end
end

Then("I cannot create the player because its name already exists") do
  @page.expect_already_exists
end

more_players_filter_matches = {
  "toto" => {
    "headers" => ['Name', 'Origin', 'Faction', 'Lists'],
    "players" => [
      ['toto', 'lyon', 'Legion', 'Absylonia1, Bethayne1'],
    ],
  },
  "lyon" => {
    "headers" => ['Name', 'Origin', 'Faction', 'Lists'],
    "players" => [
      ['tete', 'lyon', 'Khador', 'Butcher2, Koslov1'],
      ['toto', 'lyon', 'Legion', 'Absylonia1, Bethayne1'],
    ],
  },
  "kha" => {
    "headers" => ['Name', 'Faction', 'Origin', 'Lists'],
    "players" => [
      ['tete', 'Khador', 'lyon', 'Butcher2, Koslov1'],
    ],
  },
  "abs" => {
    "headers" => ['Name', 'Lists', 'Origin', 'Faction'],
    "players" => [
      ['toto', 'Absylonia1, Bethayne1', 'lyon', 'Legion'],
      ['toutou', 'Absylonia1, Lylyth2', 'paris', 'Legion'],
    ],
  },
}

Then("I see Players/List page with the matching columns first") do
  @page.expect_players_list_with_headers(more_players_filter_matches[@filter_value])
end

more_players_sort_by = {
  "Faction" => [
    ["tete"   , "lyon"    , "Khador"       , "Butcher2, Koslov1"],
    ["toto"   , "lyon"    , "Legion"       , "Absylonia1, Bethayne1"],
    ["toutou" , "paris"   , "Legion"       , "Absylonia1, Lylyth2"],
    ["tutu"   , "aubagne" , "Mercenaries"  , "Bartolo1, Cyphon1"],
    ["titi"   , "dijon"   , "Protectorate" , "Amon1, Feora1"],
    ["tyty"   , "nantes"  , "Protectorate" , "Malekus1, Severius1"],
    ["teuteu" , "paris"   , "Retribution"  , "Helynna1, Vyros1"],
  ],
  "Origin" => [
    ["tutu"   , "aubagne" , "Mercenaries"  , "Bartolo1, Cyphon1"],
    ["titi"   , "dijon"   , "Protectorate" , "Amon1, Feora1"],
    ["tete"   , "lyon"    , "Khador"       , "Butcher2, Koslov1"],
    ["toto"   , "lyon"    , "Legion"       , "Absylonia1, Bethayne1"],
    ["tyty"   , "nantes"  , "Protectorate" , "Malekus1, Severius1"],
    ["teuteu" , "paris"   , "Retribution"  , "Helynna1, Vyros1"],
    ["toutou" , "paris"   , "Legion"       , "Absylonia1, Lylyth2"],
  ],
}

Then("I see Players/List page sorted by \"$by\"") do |by|
  @page.expect_players_list(more_players_sort_by[by])
end

Then("I see Players/List page sorted by \"$by\" in reverse order") do |by|
  @page.expect_players_list(more_players_sort_by[by].reverse)
end
