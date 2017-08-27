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

    def set_players_names(indexed_players)
      indexed_players.each do |index_name|
        index = index_name[0]
        name = index_name[1]
        set_player_name(index, name)
      end
      self
    end

    def set_player_name(index, name)
      within_fieldset("Next round") do
        within(:xpath, "(.//select)[#{index+1}]") do
          select(name)
        end
      end
      self
    end

    def set_tables(n_tables)
      n_tables.times do |i|
        set_table(i, i+1)
      end
    end

    def set_table(index, value)
      within_fieldset("Next round") do
        find(:xpath, "(.//input)[#{index+1}]").set(value)
      end
    end

    def create_round
      within_fieldset("Next round") do
        click_button({value: "submit"})
      end
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

    def expect_empty_pairing(index)
      within_fieldset("Next round") do
        expect(find(:xpath, "(.//select)[#{index+1}]").value).to eq("")
      end
    end

    def expect_unpaired_players_error(names)
      within_fieldset("Next round") do
        expect(page).to have_content(Regexp.new("#{names.join(",\s+")} are not paired"))
      end
    end

    def expect_games(games)
      expected_content = games.map do |game|
        [
          game[:p1ap] || 0,
          game[:p1cp] || 0,
          game[:p1] || 0,
          game[:table] || 0,
          game[:p2] || 0,
          game[:p2cp] || 0,
          game[:p2ap] || 0,
        ]
      end
        .map { |r| r.join("\s*") }
        .join("\s*")

      within(".sd-PageContent") do
        expect(page).to have_content(Regexp.new(expected_content, "i"))
      end
    end
  end
end
