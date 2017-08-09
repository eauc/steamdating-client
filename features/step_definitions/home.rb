Given(/^I open Home page$/) do
  @page = Pages::Home.new
  @page.load
end

When(/^I test the "([^"]*)"$/) do |feature|
  @page.test feature
end
