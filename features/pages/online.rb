require_relative "./page"

module Pages
  class Online < Page
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
