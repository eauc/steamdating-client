When(/^I validate the "([^"]*)"$/) do |arg1|
  validate_prompt
end

When(/^I cancel the "([^"]*)"$/) do |arg1|
  cancel_prompt
end

When(/^I change the Prompt value to "(.*)"$/) do |value|
  set_prompt_value(value)
end

Then(/^the Alert appears with a test message$/) do
  expect_alert("This is an alert")
end

Then(/^the Confirm appears with a test message$/) do
  expect_confirm("This is a confirm")
end

Then(/^the Prompt appears with a test message and a test value$/) do
  expect_prompt("This is a prompt", 42)
end

Then(/^the "([^"]*)" disappears$/) do |arg1|
  expect_prompt_not_visible
end
