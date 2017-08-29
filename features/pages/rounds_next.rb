require_relative "./rounds"

module Pages
  class RoundsNext < Rounds

    NEXT_ROUND_FORM = "Next round"
    GAME_ROW = "//tr[count(.//select)=2 and count(.//input[@type='number'])=1]"

    def load
      super
        .start_next
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
      within_fieldset(NEXT_ROUND_FORM) do
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
      self
    end

    def set_table(index, value)
      within_fieldset(NEXT_ROUND_FORM) do
        find(:xpath, "(.//input)[#{index+1}]").set(value)
      end
      self
    end

    def create_round
      within_fieldset(NEXT_ROUND_FORM) do
        click_button({value: "submit"})
      end
      self
    end

    def expect_games_forms_for_players(players)
      nb_games = nb_games_for_players(players)

      within_fieldset(NEXT_ROUND_FORM) do
        expect(page.all(:xpath, GAME_ROW).length).to be(nb_games)
      end
      self
    end

    def expect_games_selects_for_players(players)
      nb_games = nb_games_for_players(players)
      names = players.map { |p| p["name"] }
      options = names.map { |n| "./option[@value='#{n}']" }
      selector = ".//tr//select[#{options.join(" and ")}]"

      within_fieldset(NEXT_ROUND_FORM) do
        expect(page.all(:xpath, selector).length).to be(nb_games * 2)
      end
      self
    end

    def expect_empty_pairing(index)
      within_fieldset(NEXT_ROUND_FORM) do
        expect(find(:xpath, "(.//select)[#{index+1}]").value).to eq("")
      end
      self
    end

    def expect_unpaired_players_error(names)
      within_fieldset(NEXT_ROUND_FORM) do
        expect(page).to have_content(Regexp.new("#{names.join(",\s+")} are not paired"))
      end
      self
    end
  end
end
