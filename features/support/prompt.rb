module Prompt
  include Capybara::DSL

  PROMPT_SELECTOR = ".sd-Prompt"
  VALIDATE_BUTTON_LABEL = "Ok"
  CANCEL_BUTTON_LABEL = "No"

  def set_prompt_value(value)
    within(PROMPT_SELECTOR) do
      fill_in("prompt.value", with: value)
      sleep 0.5
    end
  end

  def validate_prompt
    within(PROMPT_SELECTOR) do
      click_on(VALIDATE_BUTTON_LABEL)
    end
  end

  def cancel_prompt
    within(PROMPT_SELECTOR) do
      click_on(CANCEL_BUTTON_LABEL)
    end
  end

  def expect_prompt_not_visible
    expect(page).to have_no_selector(PROMPT_SELECTOR)
  end

  def expect_alert(message)
    within(PROMPT_SELECTOR) do
      expect(page).to have_content(message)
      expect(page).to have_button(VALIDATE_BUTTON_LABEL)
      expect(page).to have_no_button(CANCEL_BUTTON_LABEL)
    end
  end

  def expect_confirm(message)
    within(PROMPT_SELECTOR) do
      expect(page).to have_content(message)
      expect(page).to have_button(VALIDATE_BUTTON_LABEL)
      expect(page).to have_button(CANCEL_BUTTON_LABEL)
    end
  end

  def expect_prompt(message, value)
    within(PROMPT_SELECTOR) do
      expect(page).to have_content(message)
      expect(page).to have_field(with: value)
      expect(page).to have_button(VALIDATE_BUTTON_LABEL)
      expect(page).to have_button(CANCEL_BUTTON_LABEL)
    end
  end
end
