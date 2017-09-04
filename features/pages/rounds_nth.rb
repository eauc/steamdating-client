require_relative "./rounds"

module Pages
  class RoundsNth < Rounds
    def initialize(n)
      super()
      @n = n
    end

    def load
      super
        .start_nth(@n)
    end

    def filter(value)
      within(PAGE_CONTENT) do
        fill_in("Filter", with: value)
      end
      self
    end

    def sort_by(by)
      within(PAGE_CONTENT) do
        find("th", text: "Player1").click
        find("th", text: by).click
      end
      self
    end

    def invert_sort_by(by)
      within(PAGE_CONTENT) do
        find("th", text: by).click
      end
      self
    end

    def expect_games(games)
      expected_content = games.map do |game|
        [
          game[:p1ap] || 0,
          game[:p1cp] || 0,
          game[:player1] || "Phantom",
          game[:table] || 1,
          game[:player2] || "Phantom",
          game[:p2cp] || 0,
          game[:p2ap] || 0,
        ]
      end
        .map { |r| r.join("\\s+") }
        .join("\\s+")

      within(PAGE_CONTENT) do
        expect(find("tbody")).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
      end
      self
    end
  end
end
