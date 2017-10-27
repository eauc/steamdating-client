require_relative "./page"
require_relative "../mixins/prompt"

module Pages
  class Data < Page
    include Prompt

    def load
      within(NAV) do
        click_on("Data")
      end
      self
    end

    def new_tournament
      within(PAGE_CONTENT) do
        click_on("New")
      end
      validate_prompt
      self
    end

    def open(file)
      new_tournament
      within(PAGE_CONTENT) do
        file_path = ::File.absolute_path(::File.join(::File.dirname(__FILE__), "../data/", file))
        attach_file("Open", file_path, visible: false)
      end
      self
    end

    def open_t3_csv(file)
      new_tournament
      within(PAGE_CONTENT) do
        file_path = ::File.absolute_path(::File.join(::File.dirname(__FILE__), "../data/", file))
        attach_file("T3 CSV", file_path, visible: false)
      end
      self
    end

    def open_cc_json(file)
      new_tournament
      within(PAGE_CONTENT) do
        file_path = ::File.absolute_path(::File.join(::File.dirname(__FILE__), "../data/", file))
        attach_file("CC JSON", file_path, visible: false)
      end
      self
    end
  end
end
