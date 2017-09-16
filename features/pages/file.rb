require_relative "./page"

module Pages
  class File < Page
    def load
      within(NAV) do
        click_on("File")
      end
      self
    end

    def open(file)
      within(PAGE_CONTENT) do
        file_path = ::File.absolute_path(::File.join(::File.dirname(__FILE__), "../data/", file))
        attach_file("Open", file_path, visible: false)
      end
      self
    end

    def open_t3_csv(file)
      within(PAGE_CONTENT) do
        file_path = ::File.absolute_path(::File.join(::File.dirname(__FILE__), "../data/", file))
        attach_file("T3 CSV", file_path, visible: false)
      end
      self
    end
  end
end
