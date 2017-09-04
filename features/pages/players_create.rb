require_relative "./players"
require_relative "../mixins/form"

module Pages
  class PlayersCreate < Players
    include Form

    def load
      super.start_create_player
    end

    def fill_player_form(player)
      super("Create player", player)
    end

    def create_player(player)
      fill_player_form(player)
      submit
    end

    def expect_already_exists
      within(Pages::PAGE_CONTENT) do
        expect_input_error("Name", "Name already exists")
        expect_submit_disabled
      end
    end
  end
end
