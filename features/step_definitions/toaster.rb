Then(/^the Toaster appears with a test message$/) do
  expect_toaster("Ouuuuups1!")
end

Then(/^the Toaster appears with the "([^"]*)" message$/) do |message|
  expect_toaster(message)
end
