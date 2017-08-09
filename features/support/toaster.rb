module Toaster
  include Capybara::DSL

  def expect_toaster(message)
    within(".sd-Toaster") do
      expect(page).to have_content(message)
    end
    expect(page).to have_no_selector(".sd-Toaster")
  end
end
