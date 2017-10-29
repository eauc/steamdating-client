require_relative "./page"

module Pages
  class Players < Page
    def initialize
      super
      @route = "players"
    end

    def load
      within(NAV) do
        click_on("Players")
      end
      self
    end

    def start_create_player
      within(PAGE_MENU) do
        click_on("Create player")
      end
      within(PAGE_CONTENT) do
        expect(page).to have_content("Create player")
      end
      self
    end

    def fill_player_form(fieldset, player)
      within_fieldset(fieldset) do
        fill_in("Name", with: player["name"]) if player.key? "name"
        fill_in("Origin", with: player["origin"]) if player.key? "origin"
        select(player["faction"], from: "Faction") if player.key? "faction"
        player["lists"].each { |list| select(list, from: "Lists") } if player.key? "lists"
        fill_in("Notes", with: player["notes"]) if player.key? "notes"
      end
      sleep 0.5
      self
    end

    # def start_edit_player(name)
    #   within(PAGE_CONTENT) do
    #     find("tr", text: name).click
    #     expect(page).to have_content("Edit player")
    #   end
    #   self
    # end
  end
end
