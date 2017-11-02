require_relative "./rounds"

module Pages
  class RoundsNext < Rounds

    NEXT_ROUND_FORM = "Next round"
    GAME_ROW = "//tr[count(.//select)=2 and count(.//input[@type='number'])=1]"

    def load
      super
        .start_next
    end

    def enter_games(games)
      games.each_with_index do |game, i|
        set_player_name(i, :player1, game[:player1]) unless game[:player1].empty?
        set_table(i, game[:table]) unless game[:table].empty?
        set_player_name(i, :player2, game[:player2]) unless game[:player2].empty?
      end
      self
    end

    # def set_players_names(indexed_players)
    #   indexed_players.each do |index_name|
    #     index = index_name[0]
    #     name = index_name[1]
    #     set_player_name(index, name)
    #   end
    #   self
    # end

    def game_player_index(game, player)
      game * 2 + (player === :player1 ? 0 : 1)
    end

    def set_player_name(game, player, name)
      index = game_player_index(game, player)
      within_fieldset(NEXT_ROUND_FORM) do
        within(:xpath, "(.//select)[#{index+1}]") do
          select(name)
        end
      end
      self
    end

    # def set_tables(n_tables)
    #   n_tables.times do |i|
    #     set_table(i, i+1)
    #   end
    #   self
    # end

    def set_table(index, value)
      within_fieldset(NEXT_ROUND_FORM) do
        find(:xpath, "(.//input)[#{index+1}]").set(value)
      end
      self
    end

    def ask_sr_suggestion
      within(PAGE_MENU) do
        click_on("Suggest SR pairing")
      end
      self
    end

    def create_round
      within(PAGE_MENU) do
        click_button("Start round")
      end
      self
    end

    def expect_n_games_forms(nb_games)
      within_fieldset(NEXT_ROUND_FORM) do
        expect(page.all(:xpath, GAME_ROW).length).to eq(nb_games)
      end
      self
    end

    def expect_games_forms_for_players(players)
      nb_games = nb_games_for_players(players)
      expect_n_games_forms(nb_games)
    end

    def expect_games_selects_for_players(players)
      nb_games = nb_games_for_players(players)
      names = players.map { |p| p["name"] }
      options = names.map { |n| "./option[@value='#{n}']" }
      selector = ".//tr//select[#{options.join(" and ")}]"

      within_fieldset(NEXT_ROUND_FORM) do
        expect(page.all(:xpath, selector).length).to eq(nb_games * 2)
      end
      self
    end

    def expect_empty_pairing(game, player)
      index = game_player_index(game, player)
      within_fieldset(NEXT_ROUND_FORM) do
        expect(find(:xpath, "(.//select)[#{index+1}]").value).to eq("")
      end
      self
    end

    def expect_start_enabled
      within(PAGE_MENU) do
        expect(page).to have_button("Start round")
        expect(page).to have_no_button("Start round", class: "disabled")
      end
      self
    end

    def expect_start_disabled
      within(PAGE_MENU) do
        expect(page).to have_button("Start round", class: "disabled")
      end
      self
    end

    def expect_unpaired_players_error(names)
      within_fieldset(NEXT_ROUND_FORM) do
        expect(page).to have_content(Regexp.new("#{names.join(",\\s+")} are not paired"))
      end
      self
    end

    def expect_already_played(pairings)
      within_fieldset(NEXT_ROUND_FORM) do
        expected = pairings.map {|p| p.join("-")}.join(",\\s+")
        expect(page).to have_content(Regexp.new("#{expected} ha(ve|s) already been played"))
      end
      self
    end

    def expect_no_error_warning
      within_fieldset(NEXT_ROUND_FORM) do
        expect(page).to have_no_content("not paired")
        expect(page).to have_no_content("already been played")
        expect(page).to have_no_content("mirror game")
        # expect(page).to have_no_content("same-origin pairing")
      end
      self
    end

    # def expect_player_paired(name)
    #   within_fieldset(NEXT_ROUND_FORM) do
    #     expect(page).to have_select(selected: name)
    #   end
    #   self
    # end

    # def expect_player_not_paired(name)
    #   within_fieldset(NEXT_ROUND_FORM) do
    #     expect(page).to have_no_select(selected: name)
    #   end
    #   self
    # end
  end
end
