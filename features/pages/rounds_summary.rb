require_relative "./rounds"

module Pages
  class RoundsSummary < Rounds
    def go_to_round(n)
      within(PAGE_CONTENT) do
        click_on("Round ##{n}")
      end
      self
    end

    def filter(value)
      within(PAGE_CONTENT) do
        fill_in("Filter", with: value)
      end
      sleep 0.25
      self
    end

    def invert_sort_by(by)
      within(PAGE_CONTENT) do
        find("th", text: by).click
      end
      self
    end

    def expect_rounds_summary(rows)
      expected_content = rows
                           .map {|row| row.join("\\s+")}
                           .join("\\s+")
      within(PAGE_CONTENT) do
        expect(page).to have_content("Rounds summary")
        expect(find("tbody")).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
      end
      self
    end
  end
end
