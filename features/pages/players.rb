require_relative "./page"

module Pages
  class Players < Page
    def initialize
      @route = "players"
    end

    def start_create_player
      within(".sd-PageMenu") do
        click_on("Create Player")
      end
    end
  end
end
