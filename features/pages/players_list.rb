require_relative "./players"

module Pages
  class PlayersList < Players
    def filter_with(filter)
      within_table("Players") do
        fill_in(placeholder: "Filter", with: filter)
      end
      sleep 0.5
      self
    end

    def sort_by(sort)
      within_table("Players") do
        find("th", text: "Name").click
        find("th", text: sort).click
      end
      self
    end

    def invert_sort_by(sort)
      within_table("Players") do
        find("th", text: sort).click
      end
      self
    end

    def expect_player_in_list(player)
      row_text = Regexp.new player.join("\\s+")
      within_table("Players") do
        expect(page).to have_content(row_text)
      end
      self
    end

    def expect_players_list(players)
      list = players.map {|p| p.join("\\s+") }.join("\\s+")
      within_table("Players") do
        within("tbody") do
          expect(page).to have_content(Regexp.new("^\\s*#{list}\\s*$", "i"))
        end
      end
      self
    end
  end
end
