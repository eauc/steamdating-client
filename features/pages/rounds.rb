require_relative "./page"

module Pages
  class Rounds < Page
    def load
      within(NAV) do
        click_on("Rounds")
      end
      self
    end

    def start_next
      within(PAGE_MENU) do
        click_on("Next round")
      end
      self
    end

    # def start_nth(n)
    #   within(PAGE_MENU) do
    #     click_on("Round ##{n}")
    #   end
    #   self
    # end

    def nb_games_for_players(players)
      ((players.length + 1) / 2).floor
    end
  end
end
