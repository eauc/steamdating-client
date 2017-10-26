module Toaster
  include Capybara::DSL

  TOASTER = ".sd .toaster"
  def expect_toaster(message)
    within(TOASTER) do
      expect(page).to have_content(message)
      expect(page).to have_no_content(message)
    end
  end
end
