require_relative "./page"

module Pages
  class Rounds < Page

    GAME_ROW_SELECTOR = "//tr[count(.//select)=2 and count(.//input[@type='number'])=1]"

    def initialize
      @route = "rounds/all"
    end

    def start_edit_next
      within(".sd-PageMenu") do
        click_on("Next round")
      end
      self
    end

    def nb_games_for_players(players)
      ((players.length + 1) / 2).floor
    end

    def expect_games_forms_for_players(players)
      nb_games = nb_games_for_players(players)
      within_fieldset("Next round") do
        expect(page.all(:xpath, GAME_ROW_SELECTOR).length).to be(nb_games)
      end
      self
    end

    def expect_games_selects_for_players(players)
      nb_games = nb_games_for_players(players)
      names = players.map { |p| p["name"] }
      options = names.map { |n| "./option[@value='#{n}']" }
      selector = ".//tr//select[#{options.join(" and ")}]"
      within_fieldset("Next round") do
        expect(page.all(:xpath, selector).length).to be(nb_games * 2)
      end
    end
  end
end
