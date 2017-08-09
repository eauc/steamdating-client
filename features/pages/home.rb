require_relative "./page"

module Pages
  class Home < Page
    def initialize
      @name = "home"
    end

    def test(feature)
      click_on("Test #{feature}")
    end
  end
end
