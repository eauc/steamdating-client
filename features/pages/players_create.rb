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
      self
    end

    # def create_player(player)
    #   fill_player_form(player)
    #   submit
    # end

    def submit
      within(PAGE_MENU) do
        click_button("Create")
      end
      self
    end

    def expect_already_exists
      within_fieldset("Create player") do
        expect_input_error("Name", "Name already exists")
      end
      within(PAGE_MENU) do
        expect(page).to have_button("Create", class: "disabled")
      end
      self
    end
  end
end
