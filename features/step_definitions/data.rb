require_relative "../pages/data"

Given("I start a new tournament") do
  @page = Pages::Data.new
            .load
            .new_tournament
end

Given("I import T3 CSV players file \"$file\"") do |file|
  @page = Pages::Data.new
            .load
            .open_t3_csv(file)
end

Given("I import CC JSON players file \"$file\"") do |file|
  @page = Pages::Data.new
            .load
            .open_cc_json(file)
end

Given("some Players have been defined") do
  @tournament = JSON.parse(File.read("features/data/somePlayers.json"))
  Pages::Data.new
    .load
    .open("somePlayers.json")
  expect_toaster("File loaded")
  validate_prompt
end

# Given("some Players have been defined") do
#   @tournament = JSON.parse(File.read("features/data/somePlayers.json"))
#   Pages::File.new
#     .load
#     .open("somePlayers.json")
#   expect_toaster("File loaded")
#   validate_prompt
# end

# Given("more Players have been defined") do
#   @tournament = JSON.parse(File.read("features/data/morePlayers.json"))
#   Pages::File.new
#     .load
#     .open("morePlayers.json")
#   expect_toaster("File loaded")
#   validate_prompt
# end
