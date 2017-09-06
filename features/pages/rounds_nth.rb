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

    def start_edit_game_with_player(name)
      within(Pages::PAGE_CONTENT) do
        within("tbody") do
          find("tr", text: name).click
        end
      end
      self
    end

    def expect_games(games)
      expected_content = games.map do |game|
        [
          game[:p1ap] || 0,
          game[:p1cp] || 0,
          game[:player1] || "Phantom",
          game[:p1list] || "",
          game[:table] || 1,
          game[:player2] || "Phantom",
          game[:p2list] || "",
          game[:p2cp] || 0,
          game[:p2ap] || 0,
        ].reject { |c| c.is_a?(String) and c.empty? }
          .join("\\s+")
      end.join("\\s+")

      within(PAGE_CONTENT) do
        expect(find("tbody")).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
      end
      self
    end
  end
end
