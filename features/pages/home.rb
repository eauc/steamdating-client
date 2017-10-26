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
      within(PAGE_MENU) do
        click_on("Test #{feature}")
      end
      self
    end
  end
end
