require_relative "./players"

module Pages
  class PlayersList < Players
    def filter_with(filter)
      within_players_list do
        fill_in(placeholder: "Filter", with: filter)
      end
      sleep 0.5
      self
    end

    def sort_by(sort)
      within_players_list do
        find("th", text: sort).click
      end
      self
    end

    def invert_sort_by(sort)
      within_players_list do
        find("th", text: sort).click
      end
      self
    end

    def expect_player_in_list(player)
      row_text = Regexp.new player.join("\\s+")
      within_players_list do
        expect(page).to have_content(row_text)
      end
      self
    end

    def expect_player_not_in_list(name)
      within_players_list do
        within("tbody") do
          expect(page).to have_no_content(name)
        end
      end
    end

    def expect_players_list(players)
      list = players.map {|p| p.join("\\s+") }.join("\\s+")
      within_players_list do
        within("tbody") do
          expect(page).to have_content(Regexp.new("^\\s*#{list}\\s*$", "i"))
        end
      end
      self
    end
  end
end
