require_relative "./page"
require_relative "../mixins/prompt"
require_relative "../mixins/form"

module Pages
  class File < Page
    include Prompt
    include Form

    def load
      within(NAV) do
        click_on("File")
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

    def upload_tournament(data)
      within_fieldset("Upload current tournament") do
        fill_in("Name", with: data["name"])
        fill_in("Date", with: data["date"])
        sleep 0.5
        click_on("Upload")
      end
    end

    def download_online_tournament(name)
      within_table("Online tournaments") do
        find("tr", text: name).click
      end
    end

    def expect_online_tournament(tournament)
      within_table("Online tournaments") do
        expect(page).to have_content(Regexp.new("#{tournament["date"]}\\s+#{tournament["name"]}"))
      end
    end
  end
end
