require_relative "./rounds"

module Pages
  class RoundsSummary < Rounds
    # def go_to_round(n)
    #   within(PAGE_CONTENT) do
    #     click_on("Round ##{n}")
    #   end
    #   self
    # end

    def filter(value)
      within(PAGE_CONTENT) do
        fill_in("Filter", match: :first, with: value)
      end
      sleep 0.25
      self
    end

    def invert_sort_by(by)
      within(PAGE_CONTENT) do
        find("th", match: :first, text: Regexp.new("^#{by}")).click
      end
      self
    end

    def expect_rounds_summary(rows)
      expected_content = rows
                           .map {|row| row[1..-1].reject {|c| c.empty?}.join("\\s+")}
                           .join("\\s+")
      within(:table, "Rounds Summary", match: :first) do
        within("tbody") do
          # puts page.text
          # puts expected_content
          expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
        end
      end
      self
    end
  end
end
