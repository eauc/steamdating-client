require_relative "../pages/home"

Given(/^I open Home page$/) do
  @page = Pages::Home.new
            .load
end

When(/^I test the "([^"]*)"$/) do |feature|
  @page.test feature
end
