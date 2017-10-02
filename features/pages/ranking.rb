require_relative "./page"

module Pages
  class Ranking < Page
    def initialize
      @route = "ranking"
    end

    def within_list
      within_table("Ranking") do
        within("tbody") do
          yield
        end
      end
    end

    def within_list_header
      within_table("Ranking") do
        within("thead") do
          yield
        end
      end
    end

    def within_player_row(name)
      within_list do
        within("tr", text: name) do
          yield
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
      within(PAGE_CONTENT) do
        fill_in(placeholder: "Filter", with: filter)
      end
      sleep 0.5
      self
    end

    def sort_by(sort)
      within_list_header do
        find("th", text: sort).click
      end
      self
    end

    def drop_player(name)
      within_player_row(name) do
        click_on("Drop now")
      end
      self
    end

    def undrop_player(name)
      within_player_row(name) do
        click_on("Droped after round")
      end
      self
    end

    def expect_ranking_list ranking
      expect(page).to have_content("Ranking")

      within_list do
        expected_content = ranking.map {|r|
          r.reject{|c| c.is_a?(String) and c.empty?}.join("\\s+")
        }.join("\\s+")
        expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
      end
      self
    end

    def expect_bests_in_faction(bests)
      within(PAGE_CONTENT) do
        expected_content = bests.map {|r| r.reject{|c| c.empty?}.join("\\s+")}.join("\\s+")
        expect(page).to have_content(Regexp.new("Bests.*#{expected_content}", "i"))
      end
      self
    end

    def expect_bests_scores(bests)
      within(PAGE_CONTENT) do
        expected_content = bests.map {|r| r.reject{|c| c.empty?}.join("\\s+")}.join("\\s+")
        expect(page).to have_content(Regexp.new("Bests.*#{expected_content}", "i"))
      end
      self
    end

    def expect_player_droped_after(name, nth)
      within_player_row(name) do
        expect(page).to have_content("Droped after round \##{nth}")
      end
      self
    end

    def expect_player_not_droped(name)
      within_player_row(name) do
        expect(page).to have_content("Drop now")
        expect(page).to have_no_content("Droped after")
      end
      self
    end
  end
end
