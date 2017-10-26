module Prompt
  include Capybara::DSL

  PROMPT = ".sd .prompt"
  PROMPT_VALIDATE = "Ok"
  PROMPT_CANCEL = "No"

  def set_prompt_value(value)
    within(PROMPT) do
      fill_in("value", with: value)
      sleep 0.5
    end
  end

  def validate_prompt
    within(PROMPT) do
      click_on(PROMPT_VALIDATE)
    end
  end

  def cancel_prompt
    within(PROMPT) do
      click_on(PROMPT_CANCEL)
    end
  end

  def expect_prompt_not_visible
    expect(page).to have_no_selector(PROMPT)
  end

  def expect_alert(message)
    within(PROMPT) do
      expect(page).to have_content(message)
      expect(page).to have_button(PROMPT_VALIDATE)
      expect(page).to have_no_button(PROMPT_CANCEL)
    end
  end

  def expect_confirm(message)
    within(PROMPT) do
      expect(page).to have_content(message)
      expect(page).to have_button(PROMPT_VALIDATE)
      expect(page).to have_button(PROMPT_CANCEL)
    end
  end

  def expect_prompt(message, value)
    within(PROMPT) do
      expect(page).to have_content(message)
      expect(page).to have_field(with: value)
      expect(page).to have_button(PROMPT_VALIDATE)
      expect(page).to have_button(PROMPT_CANCEL)
    end
  end
end
