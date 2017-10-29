require_relative "./players"

module Pages
  class PlayersEdit < Players
    def load(player)
      super()
        .start_edit_player(player)
    end

    def fill_player_form(player)
      super("Edit player", player)
      self
    end

    def edit_player(data)
      fill_player_form(data)
      submit
      self
    end

    def submit
      within(PAGE_MENU) do
        click_button("Save")
      end
      self
    end

    def delete_player
      within(PAGE_MENU) do
        click_on("Delete")
      end
      self
    end
  end
end
