module Form
  include Capybara::DSL

  def expect_input_error(name, error)
    expect(page).to have_field(name, class: "error")
    expect(page).to have_content(error)
  end

  # def expect_submit_disabled
  #   expect(page).to have_css("button[type='submit']", class: "sd-Form-disabled")
  # end

  # def expect_submit_enabled
  #   expect(page).to have_css("button[type='submit']")
  #   expect(page).to have_no_css("button[type='submit']", class: "sd-Form-disabled")
  # end
end
