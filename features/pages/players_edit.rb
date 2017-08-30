require_relative "./players"

module Pages
  class PlayersEdit < Players
    def load(player)
      super()
        .start_edit_player(player)
    end

    def fill_player_form(player)
      super("Edit player", player)
    end

    def edit_player(data)
      fill_player_form(data)

      within(PAGE_CONTENT) do
        click_button({value: "submit"})
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