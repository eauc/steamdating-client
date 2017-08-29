require_relative "./players"

module Pages
  class PlayersList < Players
    def filter_with(filter)
      within(PAGE_CONTENT) do
        fill_in(placeholder: "Filter", with: filter)
      end
      sleep 0.5
      self
    end

    def sort_by(sort)
      within(PAGE_CONTENT) do
        find("th", text: "Name").click
        find("th", text: sort).click
      end
      self
    end

    def invert_sort_by(sort)
      within(PAGE_CONTENT) do
        find("th", text: sort).click
      end
      self
    end

    def expect_player_in_list player
      values = [
        player["name"],
        player["origin"],
        player["faction"],
        player["lists"].sort,
      ]
      row_text = Regexp.new values.flatten.join(".*")
      within(PAGE_CONTENT) do
        expect(page).to have_selector("tr", text: row_text)
      end
      self
    end

    def expect_players_list(players)
      list = Regexp.new(players.map {|p| p.join(".*") }.join(".*"), "i")
      within(PAGE_CONTENT) do
        expect(page).to have_content(list)
      end
      self
    end

    def expect_players_list_with_headers(match)
      headers = match["headers"].join(".*")
      players = match["players"].map {|p| p.join(".*") }.join(".*")
      list = Regexp.new("#{headers}.*#{players}", "i")
      within(PAGE_CONTENT) do
        expect(page).to have_content(list)
      end
      self
    end
  end
end
