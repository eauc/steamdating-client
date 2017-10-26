require "capybara/cucumber"
# require_relative "../mixins/form"
require_relative "../mixins/prompt"
require_relative "../mixins/toaster"

# Capybara.default_driver = :selenium
Capybara.default_driver = :selenium_chrome
Capybara.app_host = "http://localhost:3000"
Capybara.run_server = false
Capybara.default_max_wait_time = 5

World(# Form,
  Prompt,
  Toaster)

Before do
  visit("/")
end
