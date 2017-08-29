require_relative "./players"

module Pages
  class PlayersCreate < Players
    def load
      super.start_create_player
    end

    def fill_player_form(player)
      super("Create player", player)
    end

    def create_player(player)
      fill_player_form(player)
      within(PAGE_CONTENT) do
        click_button({value: "submit"})
      end
      self
    end
  end
end
