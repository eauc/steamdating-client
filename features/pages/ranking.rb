require_relative "./page"

module Pages
  class Ranking < Page
    def initialize
      @route = "ranking"
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
      within(PAGE_CONTENT) do
        within(:xpath, "(.//thead)[1]") do
          find("th", text: sort).click
        end
      end
    end

    def expect_ranking_list ranking
      expect(page).to have_content("Ranking")

      within(PAGE_CONTENT) do
        within(:xpath, "(.//tbody)[1]") do
          expected_content = ranking.map {|r|
            r.reject{|c| c.is_a?(String) and c.empty?}.join("\\s+")
          }.join("\\s+")
          expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
        end
      end
    end

    def expect_bests_in_faction(bests)
      within(PAGE_CONTENT) do
        expected_content = bests.map {|r| r.reject{|c| c.empty?}.join("\\s+")}.join("\\s+")
        expect(page).to have_content(Regexp.new("Bests.*#{expected_content}", "i"))
      end
    end

    def expect_bests_scores(bests)
      within(PAGE_CONTENT) do
        expected_content = bests.map {|r| r.reject{|c| c.empty?}.join("\\s+")}.join("\\s+")
        expect(page).to have_content(Regexp.new("Bests.*#{expected_content}", "i"))
      end
    end
  end
end
