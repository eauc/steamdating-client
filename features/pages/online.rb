require_relative "./page"
require_relative "../mixins/prompt"

module Pages
  class Online < Page
    include Prompt

    def load
      within(NAV) do
        click_on("Online")
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
      self
    end

    def download_online_tournament(name)
      within_table("Online tournaments") do
        find("tr", text: name).click
      end
      validate_prompt
      self
    end

    # def follow_current_tournament()
    #   within(PAGE_CONTENT) do
    #     click_on("Follow");
    #   end
    #   click_on("http")
    # end

    def expect_online_tournament(tournament)
      within_table("Online tournaments") do
        within("tbody") do
          expect(page).to have_content(Regexp.new("#{tournament["date"]}\\s+#{tournament["name"]}"))
        end
      end
      self
    end
  end
end
