from sys import stdin, exit
from datetime import datetime

MAX_DAYS = 60
GREEN = "\033[92m"
RED = "\033[91m"
END = "\033[0m"


class ExpiryDate:
    def __init__(self, expiry_str):
        self.expiry_date = self.parse_expiry_date(expiry_str)
        self.check_expiry()

    def parse_expiry_date(self, date_str):
        return datetime.strptime(date_str.strip(), '%b %d %H:%M:%S %Y %Z')

    def check_expiry(self):
        self.days_left = (self.expiry_date - datetime.now()).days
        self.is_out_of_date = self.days_left <= MAX_DAYS

    def __str__(self):
        decoration = '=' * 75
        if self.is_out_of_date:
            message = f"WARNING: The certificate will expire in {self.days_left} days on {self.expiry_date}"
            color = RED
        else:
            message = f"INFO: The certificate is okay."
            color = GREEN
        return f"{color}{decoration}\n{message}\n{decoration}{END}"


if __name__ == "__main__":
    expiry_date_str = stdin.read().strip()
    expiry_date = ExpiryDate(expiry_date_str)
    print(expiry_date)
    exit(int(expiry_date.is_out_of_date))
