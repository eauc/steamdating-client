require "json"
require_relative "../pages/file"
require_relative "../pages/players_create"
require_relative "../pages/players_edit"
require_relative "../pages/players_list"

Given(/^I open Players page$/) do
  @page = Pages::PlayersList.new
            .load
end

Given(/^more Players have been defined$/) do
  @tournament = JSON.parse(File.read("features/data/morePlayers.json"))
  Pages::File.new
    .load
    .open("morePlayers.json")
  expect_toaster("File loaded")
  validate_prompt
end

Given(/^some Players have been defined$/) do
  @tournament = JSON.parse(File.read("features/data/somePlayers.json"))
  Pages::File.new
    .load
    .open("somePlayers.json")
  expect_toaster("File loaded")
  validate_prompt
end

When(/^I filter the Players list with "([^"]*)"$/) do |filter|
  @filter_value = filter
  @page.filter_with(filter)
end

When(/^I sort the Players list by "([^"]*)"$/) do |sort|
  @sort_value = sort
  @page.sort_by(sort)
end

When(/^I invert the sort order$/) do
  @page.invert_sort_by(@sort_value)
end

When(/^I start to create Player$/) do
  @page.start_create_player
end

When(/^I create a valid Player$/) do
  @created_player = {
    "name" => "Toto",
    "origin" => "Lyon",
    "faction" => "Legion",
    "lists" => ["Fyanna2", "Absylonia1"],
    "notes" => "Notes sur le joueur",
  }
  @page = Pages::PlayersCreate.new
            .load
            .create_player(@created_player)
end

When(/^I try to create a Player whose name already exists$/) do
  @page = Pages::PlayersCreate.new
            .load
            .fill_player_form({"name" => "toto"})
end

When(/^I edit a Player$/) do
  @updated_player = {
    "name" => "tete",
    "origin" => "paris",
    "faction" => "Cryx",
    "lists" => ["Agathia1","Asphyxious2"],
  }
  @outdated_player = @tournament["players"][1]
  @page = Pages::PlayersEdit.new
            .load(@outdated_player)
            .edit_player(@updated_player)
end

When(/^I delete a Player$/) do
  @deleted_player = @tournament["players"][1]
  @page = Pages::PlayersEdit.new
            .load(@deleted_player)
            .delete_player
  validate_prompt
end

Then(/^I can edit the Player information$/) do
  within(Pages::PAGE_CONTENT) do
    expect(page).to have_field("Name")
    expect(page).to have_field("Origin")
    expect(page).to have_field("Faction")
    expect(page).to have_field("Lists")
    expect(page).to have_field("Notes")
  end
end

Then(/^I see the created Player in the Players list$/) do
  expect(Pages::Players.new).to be_loaded
  Pages::PlayersList.new
    .expect_player_in_list(@created_player)
end

Then(/^I see the updated Player in the Players list$/) do
  expect(Pages::Players.new).to be_loaded
  Pages::PlayersList.new
    .expect_player_in_list(@updated_player)
  within(Pages::PAGE_CONTENT) do
    expect(page).to have_no_content(@outdated_player["name"])
  end
end

Then(/^I cannot create the invalid Player$/) do
  within(Pages::PAGE_CONTENT) do
    expect_input_error("Name", "Name already exists")
    expect_submit_disabled
  end
end

Then(/^I do not see the deleted Player in the Players list$/) do
  expect(Pages::Players.new).to be_loaded
  within(Pages::PAGE_CONTENT) do
    expect(page).to have_no_content(@deleted_player["name"])
  end
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

Then(/^I see the matching Players with the matching columns first$/) do
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

Then(/^I see the Players sorted by "([^"]*)"$/) do |sort|
  @page.expect_players_list(more_players_sort_by[sort])
end

Then(/^I see the Players sorted by "([^"]*)" in revert order$/) do |sort|
  @page.expect_players_list(more_players_sort_by[sort].reverse)
end
