require_relative "./page"

module Pages
  class Home < Page
    def load
      within(NAV) do
        click_on("Home")
      end
      self
    end

    def test(feature)
      click_on("Test #{feature}")
      self
    end
  end
end
