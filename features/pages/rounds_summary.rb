require_relative "./rounds"

module Pages
  class RoundsSummary < Rounds
    def filter(value)
      within(PAGE_CONTENT) do
        fill_in("Filter", match: :first, with: value)
      end
      sleep 0.25
      self
    end

    def sort_by(by)
      match = ["#","Player"].include?(by) ? 1 : 0
      within(PAGE_CONTENT) do
        all("th", text: Regexp.new("^#{by}"))[match].click
      end
      self
    end

    def expect_rounds_summary(rows)
      expected_content = rows
                           .map {|row| row.reject {|c| c.empty?}.join("\\s+")}
                           .join("\\s+")
      within(:table, "Rounds", match: :first) do
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
