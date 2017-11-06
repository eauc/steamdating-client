require_relative "./page"

module Pages
  class Ranking < Page
    def within_list
      within(:table, "Rankings", match: :first) do
        yield
      end
      self
    end

    def within_player_row(name)
      within_list do
        within("tbody") do
          within("tr", text: name) do
            yield
          end
        end
      end
    end

    def load
      within(NAV) do
        click_on("Ranking")
      end
      self
    end

    def filter_with(filter)
      within_list do
        fill_in("Filter", with: filter)
      end
      sleep 0.5
      self
    end

    def sort_by(sort)
      within_list do
        within("thead") do
          find("th", text: sort).click
        end
      end
      self
    end

    def drop_player(name)
      within_player_row(name) do
        click_on("Click to drop")
      end
      self
    end

    def undrop_player(name)
      within_player_row(name) do
        click_on("Click to un-drop")
      end
      self
    end

    def expect_ranking_list(ranking)
      within_list do
        within("tbody") do
          expected_content = ranking.map {|r|
            r.reject{|c| c.is_a?(String) and c.empty?}.join("\\s+")
          }.join("\\s+")
          expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
        end
      end
      self
    end

    def expect_bests_in_faction(bests)
      within_table("Bests in factions") do
        within("tbody") do
          expected_content = bests.map {|r| r.reject{|c| c.empty?}.join("\\s+")}.join("\\s+")
          expect(page).to have_content(Regexp.new("^#{expected_content}$", "i"))
        end
      end
      self
    end

    def expect_bests_scores(bests)
      within_table("Bests scores") do
        within("tbody") do
          expected_content = bests.map {|r| r.reject{|c| c.empty?}.join("\\s+")}.join("\\s+")
          expect(page).to have_content(Regexp.new("^#{expected_content}$", "i"))
        end
      end
      self
    end

    def expect_player_droped_after(name, nth)
      within_player_row(name) do
        expect(page).to have_content("Round \##{nth}")
      end
      self
    end

    def expect_player_not_droped(name)
      within_player_row(name) do
        expect(page).to have_content("Click to drop")
        expect(page).to have_no_content("Droped after")
      end
      self
    end
  end
end
