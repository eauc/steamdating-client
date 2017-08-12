Given(/^I open Players page$/) do
  @page = Pages::Players.new.load
end

Given(/^some Players have been defined$/) do
  Pages::File.new
    .load
    .open("somePlayers.json")
  expect_toaster("File loaded")
  validate_prompt
end

When(/^I start to create Player$/) do
  @page.start_create_player
end

When(/^I create a valid Player$/) do
  @created_player = {
    name: "Toto",
    origin: "Lyon",
    faction: "Legion",
    lists: ["Fyanna2", "Absylonia1"],
    notes: "Notes sur le joueur",
  }
  @page.create_player(@created_player)
end

When(/^I try to create a Player whose name already exists$/) do
  @page
    .start_create_player
    .fill_player_form({name: "toto"})
end

Then(/^I can edit the Player information$/) do
  within(".sd-PageContent") do
    expect(page).to have_field("Name")
    expect(page).to have_field("Origin")
    expect(page).to have_field("Faction")
    expect(page).to have_field("Lists")
    expect(page).to have_field("Notes")
  end
end

Then(/^I see the created Player in the Players list$/) do
  @page.expect_player_in_list(@created_player)
end

Then(/^I cannot create the invalid Player$/) do
  within(".sd-PageContent") do
    expect_input_error("Name", "Name already exists")
    expect_submit_disabled
  end
end
