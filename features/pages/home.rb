require_relative "./page"

module Pages
  class Home < Page
    def initialize
      @route = "home"
    end

    def test(feature)
      click_on("Test #{feature}")
    end
  end
end
